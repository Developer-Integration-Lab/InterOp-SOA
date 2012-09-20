/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.helper;


import org.hibernate.Hibernate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;

/**
 *
 * @author Sunil.Bhaskarla
 */
public class MySQLDialectEx extends org.hibernate.dialect.MySQLDialect {

    public MySQLDialectEx() {
        super();
        registerFunction("bit_or", new StandardSQLFunction("BIT_OR", Hibernate.INTEGER));
        registerFunction("bitwise_and", new VarArgsSQLFunction(Hibernate.INTEGER, "", "&", ""));
        registerFunction("bitwise_or", new VarArgsSQLFunction(Hibernate.INTEGER, "", "|", ""));
    }
}