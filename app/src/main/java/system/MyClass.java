package system;

import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.security.URIParameter;
import java.util.Map;

public class MyClass {
    public static void main(String[] args) throws Exception{
        // create a new url class loader, this can be used to load a jar or classes directory
        URL pluginClass = new File("./myplugin").toURI().toURL();
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{pluginClass}, MyClass.class.getClassLoader());

        // load a dynamic TestClass class
        Class loadedMyClass = urlClassLoader.loadClass("TestClass");

        // read my plugin security policy
        URIParameter myPolicyPath = new URIParameter(MyClass.class.getResource("/my.policy").toURI());
        Policy policy = Policy.getInstance("JavaPolicy", myPolicyPath);
        MyPolicy myPolicy = new MyPolicy(ImmutableMap.of(pluginClass.getPath(), policy));
        Policy.setPolicy(myPolicy);
        // install the security manager
        System.setSecurityManager(new SecurityManager());


        System.out.println("Loaded class: " + loadedMyClass.getName());

        Object myClassObject = loadedMyClass.getConstructor().newInstance();
        Method method = loadedMyClass.getMethod("foobar");
        System.out.println("Invoked method: " + method.getName());
        method.invoke(myClassObject);

    }


}

class MyPolicy extends Policy {
    //custom classes with policy mapping
    private final Map<String, Policy> plugins;

    MyPolicy(Map<String, Policy> plugins) {
        this.plugins = plugins;
    }

    @Override
    public boolean implies(ProtectionDomain domain, Permission permission) {
        CodeSource codeSource = domain.getCodeSource();
        if (codeSource == null) {
            return false;
        }

        URL location = codeSource.getLocation();
        if (location != null) {
            //get the custom plugin policy rules and validate the permissions
            Policy plugin = this.plugins.get(location.getFile());
            if (plugin != null) {
                return plugin.implies(domain, permission);
            }
        }
        return applicationPermissions().implies(permission);
    }

    private PermissionCollection applicationPermissions() {
        Permissions permissions = new Permissions();
        permissions.add(new AllPermission());
        return permissions;
    }
}
