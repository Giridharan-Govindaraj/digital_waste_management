package com.example.generator;

import com.squareup.javapoet.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.lang.model.element.Modifier;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Generator {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/yourdb";
        String user = "youruser";
        String password = "yourpassword";

        Connection conn = DriverManager.getConnection(url, user, password);

        String sql = """
            SELECT class_name, field_name, field_type, field_order, is_nullable, max_length
            FROM pojo_definition
            ORDER BY class_name, field_order
            """;

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        // className -> fields
        Map<String, List<FieldDef>> classMap = new LinkedHashMap<>();

        while (rs.next()) {
            String className = rs.getString("class_name");
            String fieldName = rs.getString("field_name");
            String fieldType = rs.getString("field_type");
            int fieldOrder = rs.getInt("field_order");
            boolean nullable = rs.getBoolean("is_nullable");
            int maxLength = rs.getInt("max_length");
            Integer maxLen = rs.wasNull() ? null : maxLength;

            classMap.computeIfAbsent(className, k -> new ArrayList<>())
                    .add(new FieldDef(fieldName, fieldType, fieldOrder, nullable, maxLen));
        }

        for (var entry : classMap.entrySet()) {
            String className = entry.getKey();
            List<FieldDef> fields = entry.getValue();

            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC);

            List<FieldSpec> fieldSpecs = new ArrayList<>();
            List<MethodSpec> methods = new ArrayList<>();
            MethodSpec.Builder allArgConstructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC);

            MethodSpec.Builder noArgConstructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC);

            for (FieldDef field : fields) {
                TypeName type = getTypeName(field.type);
                String name = field.name;

                FieldSpec.Builder fieldBuilder = FieldSpec.builder(type, name, Modifier.PRIVATE);

                // Add @NotNull if not nullable
                if (!field.nullable) {
                    fieldBuilder.addAnnotation(ClassName.get(NotNull.class));
                }

                // Add @Size if String and maxLength is present
                if ("string".equalsIgnoreCase(field.type) && field.maxLength != null) {
                    AnnotationSpec sizeAnn = AnnotationSpec.builder(ClassName.get(Size.class))
                            .addMember("max", "$L", field.maxLength)
                            .build();
                    fieldBuilder.addAnnotation(sizeAnn);
                }

                FieldSpec fieldSpec = fieldBuilder.build();
                fieldSpecs.add(fieldSpec);

                // Getter
                MethodSpec getter = MethodSpec.methodBuilder("get" + capitalize(name))
                        .addModifiers(Modifier.PUBLIC)
                        .returns(type)
                        .addStatement("return this.$N", name)
                        .build();
                methods.add(getter);

                // Setter
                MethodSpec setter = MethodSpec.methodBuilder("set" + capitalize(name))
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(type, name)
                        .addStatement("this.$N = $N", name, name)
                        .build();
                methods.add(setter);

                // All-arg constructor
                allArgConstructor.addParameter(type, name)
                        .addStatement("this.$N = $N", name, name);
            }

            // Add fields
            fieldSpecs.forEach(classBuilder::addField);

            // Add constructors and methods
            classBuilder.addMethod(noArgConstructor.build());
            classBuilder.addMethod(allArgConstructor.build());
            methods.forEach(classBuilder::addMethod);

            // Build and write file
            JavaFile javaFile = JavaFile.builder("com.generated", classBuilder.build())
                    .build();

            javaFile.writeTo(Paths.get("target/generated-sources/pojos"));
            System.out.println("Generated: " + className);
        }

        conn.close();
    }

    static TypeName getTypeName(String type) {
        return switch (type.toLowerCase()) {
            case "int", "integer" -> TypeName.INT;
            case "long" -> TypeName.LONG;
            case "double" -> TypeName.DOUBLE;
            case "boolean" -> TypeName.BOOLEAN;
            case "float" -> TypeName.FLOAT;
            case "string" -> ClassName.get(String.class);
            case "date" -> ClassName.get("java.util", "Date");
            default -> ClassName.bestGuess(type);
        };
    }

    static String capitalize(String s) {
        return s == null || s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    record FieldDef(String name, String type, int order, boolean nullable, Integer maxLength) {}
}
