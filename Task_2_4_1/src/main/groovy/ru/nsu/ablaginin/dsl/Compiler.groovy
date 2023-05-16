package ru.nsu.ablaginin.dsl


import org.codehaus.groovy.control.CompilerConfiguration
import ru.nsu.ablaginin.Main

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
}
