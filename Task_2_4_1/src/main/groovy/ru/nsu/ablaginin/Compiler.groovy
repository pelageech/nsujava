package ru.nsu.ablaginin

import org.codehaus.groovy.control.CompilerConfiguration
import org.gradle.tooling.BuildException
import org.gradle.tooling.GradleConnector

class Compiler {
    static Object compile(File file, Class clazz) {
        CompilerConfiguration cc = new CompilerConfiguration()
        cc.setScriptBaseClass(DelegatingScript.class.getName()) // благодаря этой настройке все создаваемые groovy скрипты будут наследоваться от DelegatingScript
        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc)
        DelegatingScript script = (DelegatingScript)sh.parse(file)
        Object obj = clazz.getDeclaredConstructor().newInstance()
        script.setDelegate(obj)
        script.run()
        return obj
    }

    static boolean buildTest(File projectDir) {
        if (!projectDir.exists()) {
            throw new FileNotFoundException("file " + projectDir.path + " not found")
        }

        var conn = GradleConnector.newConnector().forProjectDirectory(projectDir).connect()

        try {
            conn.newBuild().forTasks("test").run()
        } catch (BuildException e) {
            println "Failed to build"
            println e.getMessage()
            return false
        }
        return true
    }
}
