package com.abelhu.annotationcode

import com.abelhu.annotation.GreetingGenerator
import com.google.auto.service.AutoService
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

@AutoService(Processor::class) // for registering the service
@SupportedSourceVersion(SourceVersion.RELEASE_8) // support java 8
@SupportedOptions(FileGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class FileGenerator: AbstractProcessor(){
    companion object{
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    /**
     * 设置支持的版本
     */
    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    /**
     * 添加需要支持的注解
     */
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(GreetingGenerator::class.java.canonicalName)
    }

    override fun process(set: MutableSet<out TypeElement>?, environment: RoundEnvironment?): Boolean {
        environment?.getElementsAnnotatedWith(GreetingGenerator::class.java)?.forEach {
            if (it.kind == ElementKind.CLASS) {
                val name = it.simpleName.toString()
                val pack = processingEnv.elementUtils.getPackageOf(it).toString()
                generateClass(name, pack)
            }
        }
        return true
    }

    private fun generateClass(className: String, pack: String){
        val fileName = "Generated_$className"
        val fileContent = KotlinClassBuilder(fileName,pack).getContent()

        val kaptKotlinGeneratedDir = "${processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()}/${pack.replace(".", "/")}"
        val file = File(File(kaptKotlinGeneratedDir).apply { mkdirs() }, "$fileName.kt")


        file.writeText(fileContent)
    }

}