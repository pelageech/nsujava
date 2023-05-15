package ru.nsu.ablaginin


import org.codehaus.groovy.control.CompilerConfiguration
import ru.nsu.ablaginin.dsl.DSL
import ru.nsu.ablaginin.git.GitWorks

CompilerConfiguration cc = new CompilerConfiguration()
cc.setScriptBaseClass(DelegatingScript.class.getName()) // благодаря этой настройке все создаваемые groovy скрипты будут наследоваться от DelegatingScript
GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc)
DelegatingScript script = (DelegatingScript)sh.parse(new File("config/config.groovy"))
DSL dsl = new DSL()
script.setDelegate(dsl)
// благодаря предыдущей строчке run() выполнится "в контексте" объекта config и присвоит ему поля name и description
script.run()
System.out.println(dsl.toString())

var git = new GitWorks()
git.clone("artyom.groovy")
git.pull(dsl.getStudent().url, "master")
