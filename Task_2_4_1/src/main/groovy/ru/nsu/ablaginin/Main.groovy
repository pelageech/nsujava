package ru.nsu.ablaginin


import org.codehaus.groovy.control.CompilerConfiguration
import ru.nsu.ablaginin.bricks.Student

CompilerConfiguration cc = new CompilerConfiguration()
cc.setScriptBaseClass(DelegatingScript.class.getName()) // благодаря этой настройке все создаваемые groovy скрипты будут наследоваться от DelegatingScript
GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc)
DelegatingScript script = (DelegatingScript)sh.parse(new File("config/config.groovy"))
Student s = new Student()
script.setDelegate(s)
// благодаря предыдущей строчке run() выполнится "в контексте" объекта config и присвоит ему поля name и description
script.run()
System.out.println(s.toString())