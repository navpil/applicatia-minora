package ua.lviv.navpil;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class LdapToSqlTest {

    @Test
    public void ldapToSql() {

        LdapToSql ldapToSql = new LdapToSql();
        // Examples taken from:
        // https://docs.oracle.com/cd/E19396-01/817-7616/ldurl.html
        // This is how query looks like:
        // ldap[s]://hostname:port/base_dn?attributes?scope?filter

        List<String> examples = listOf(
                "ldap://ldap.example.com/dc=example,dc=com",
                "ldap://ldap.example.com/dc=example,dc=com?postalAddress",
                "ldap://ldap.example.com/cn=David%20Brent,dc=example,dc=com?cn,mail",
                "ldap://ldap.example.com/dc=example,dc=com??sub?(sn=Jensen)",
                "ldap://ldap.example.com/dc=example,dc=com??sub?((sn=Jensen&oj=XYZ)|name=John)",
                "ldap://ldap.example.com/dc=example,dc=com?objectClass?one"
        );

        for (String example : examples) {
            System.out.println(example);
            System.out.println("   maps to    ");
            System.out.println(ldapToSql.ldapToSql(example));
            System.out.println();
        }


    }

    private List<String> listOf(String ... s) {
        return Arrays.asList(s);

    }
}