package com.abelhu.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.ide.common.internal.WaitableExecutor

class CustomTransform : Transform() {
    /**
     * 最终会成为gradleTask的名字，类似transformClassesWithCustomTransformForDebug，具体参见TransformManager#getTaskNamePrefix
     */
    override fun getName(): String = CustomTransform::class.java.simpleName

    /**
     * 过滤器，只处理CONTENT_CLASS
     */
    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    /**
     * 过滤器，处理整个项目
     */
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.SCOPE_FULL_PROJECT

    /**
     * 增量编译，使用增量编译可以提升项目编译速度
     */
    override fun isIncremental() = true

    /**
     * 处理之后的class、jar文件在/build/intermediates/transforms/CustomTransform/下查找
     * 如果拿取了getInputs()的输入进行消费，则transform后必须再输出给下一级
     * 如果拿取了getReferencedInputs()的输入，则不应该被transform
     * 是否增量编译要以transformInvocation.isIncremental()为准
     */
    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)
        // 是否是增量编译，如果不是增量编译，清空所有输出，重新再来
        if (!transformInvocation.isIncremental) transformInvocation.outputProvider.deleteAll()
        // 消费型输入，可以从中获取jar包和class文件夹路径。需要输出给下一个任务，判断jarInput的状态
        transformInvocation.inputs.forEach { input -> input.jarInputs.forEach { jarInput -> jarInput.status } }
        // 引用型输入，无需输出
        transformInvocation.referencedInputs
        // OutputProvider管理输出路径，如果消费型输入为空，你会发现OutputProvider == null
        transformInvocation.outputProvider
        // 使用并发处理，同样可以提升项目编译速度
        val executor = WaitableExecutor.useGlobalSharedThreadPool()
        // 这里模拟并发处理
        executor.execute { } // 将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
        executor.execute { } // 将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
        // 等待所有任务完成
        executor.waitForTasksWithQuickFail<Any>(true)
    }
}