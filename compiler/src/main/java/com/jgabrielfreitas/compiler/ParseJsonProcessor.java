package com.jgabrielfreitas.compiler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.jgabrielfreitas.compiler.ParseJsonProcessor")
public class ParseJsonProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        StringBuilder builder = new StringBuilder()
                .append("package com.stablekernel.annotationprocessor.generated;\n\n")
                .append("public class GeneratedClass {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");


        // for each javax.lang.model.element.Element annotated with the CustomAnnotation
        for (Element element : roundEnv.getElementsAnnotatedWith(ParseJson.class)) {
            String objectType = element.getSimpleName().toString();



            // this is appending to the return statement
            builder.append(objectType).append(" says hello!\\n");
        }


        builder.append("\";\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class



        try { // write the file
            JavaFileObject source = processingEnv.getFiler().createSourceFile("com.stablekernel.annotationprocessor.generated.GeneratedClass");


            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }


        return true;
    }

    private void writeBuilderFile(String className, Map<String, String> setterMap) throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        String builderClassName = className + "Json";
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.print("public class ");
            out.print(builderSimpleClassName);
            out.println(" {");
            out.println();

            out.print("    private ");
            out.print(simpleClassName);
            out.print(" object = new ");
            out.print(simpleClassName);
            out.println("();");
            out.println();

            out.print("    public ");
            out.print(simpleClassName);
            out.println(" build() {");
            out.println("        return object;");
            out.println("    }");
            out.println();

//            setterMap.entrySet().forEach(setter -> {
//                String methodName = setter.getKey();
//                String argumentType = setter.getValue();
//
//                out.print("    public ");
//                out.print(builderSimpleClassName);
//                out.print(" ");
//                out.print(methodName);
//
//                out.print("(");
//
//                out.print(argumentType);
//                out.println(" value) {");
//                out.print("        object.");
//                out.print(methodName);
//                out.println("(value);");
//                out.println("        return this;");
//                out.println("    }");
//                out.println();
//            });

            out.println("}");
        }
    }

}
