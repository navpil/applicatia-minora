package ua.lviv.navpil;

import java.util.Arrays;

public class LdapToSql {

    public String ldapToSql(String ldapQuery) {
        //"ldap://ldap.example.com/dc=example,dc=com[?][?]sub?(sn=Jensen)",

        String sql = "";

        int firstIndex = ldapQuery.indexOf("//");
        int serverEnd = ldapQuery.indexOf('/', firstIndex + 2);
        String dbName = ldapQuery.substring(0, serverEnd);

        String pastServerName = ldapQuery.substring(serverEnd + 1);
        String[] split = pastServerName.split("\\?");
        String tableName = split[0];

        // ldap[s]://hostname:port/base_dn?attributes?scope?filter
        String attributes = split.length > 1 ? split[1] : null;
        String scope = split.length > 2 ? split[2] : null;
        String filter = split.length > 3 ? split[3] : null;

        String sqlWhere = "";
        if (filter != null && !filter.isEmpty()) {
            sqlWhere = "\nWHERE " + filter.replaceAll("&", " AND ")
                    .replaceAll("\\|", " OR ")
                    .replaceAll("!", " NOT ");

        }

        String selectFrom = (attributes == null || attributes.isEmpty()) ? " * " : attributes;

        String sqlScope = " ";
        if ("one".equals(scope)) {
            sqlScope = " [CHILD ENTRIES] ";
        } else if ("sub".equals(scope)) {
            sqlScope = " [ALL TREE] ";
        }

        return "USE " + dbName + ";\n" +
                "SELECT" + sqlScope + selectFrom + "\n" +
                "FROM " + tableName
                + sqlWhere;

    }

}
