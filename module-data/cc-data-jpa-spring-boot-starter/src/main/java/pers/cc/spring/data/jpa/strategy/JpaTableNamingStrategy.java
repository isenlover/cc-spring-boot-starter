package pers.cc.spring.data.jpa.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

import java.util.Locale;

/**
 * 此类覆盖默认的命名方式
 * 驼峰转下划线
 * 当类名有PO结尾时，自动删除
 *
 * @author chengce
 * @version 2018-06-04 15:55
 */
public class JpaTableNamingStrategy extends SpringPhysicalNamingStrategy {
  public JpaTableNamingStrategy() {
  }

  public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    return this.apply(name, jdbcEnvironment);
  }

  public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    return this.apply(name, jdbcEnvironment);
  }

  public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    return this.apply(name, jdbcEnvironment);
  }

  public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    return this.apply(name, jdbcEnvironment);
  }

  public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    return this.apply(name, jdbcEnvironment);
  }

  private Identifier apply(Identifier name, JdbcEnvironment jdbcEnvironment) {
    if (name == null) {
      return null;
    } else {
      String simpleName = name.getText();
      if (simpleName.endsWith("PO")) {
        simpleName = simpleName.substring(0, simpleName.lastIndexOf("PO"));
      }
      StringBuilder builder = new StringBuilder(simpleName.replace('.', '_'));

      for (int i = 1; i < builder.length() - 1; ++i) {
        if (this.isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i), builder.charAt(i + 1))) {
          builder.insert(i++, '_');
        }
      }

      return this.getIdentifier(builder.toString(), name.isQuoted(), jdbcEnvironment);
    }
  }

  protected Identifier getIdentifier(String name, boolean quoted, JdbcEnvironment jdbcEnvironment) {
    if (this.isCaseInsensitive(jdbcEnvironment)) {
      name = name.toLowerCase(Locale.ROOT);
    }

    return new Identifier(name, quoted);
  }

  protected boolean isCaseInsensitive(JdbcEnvironment jdbcEnvironment) {
    return true;
  }

  private boolean isUnderscoreRequired(char before, char current, char after) {
    return Character.isLowerCase(before) && Character.isUpperCase(current) && Character.isLowerCase(after);
  }
}
