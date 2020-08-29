package com.wutsi.core.hibernate

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment


/**
 * Copy of {@link https://github.com/vladmihalcea/hibernate-types/blob/master/hibernate-types-5/src/main/java/com/vladmihalcea/hibernate/type/util/CamelCaseToSnakeCaseNamingStrategy.java}
 * Just reformat the column name, not the table name.
 */
class CamelCaseToSnakeCaseNamingStrategy : PhysicalNamingStrategyStandardImpl() {
    val CAMEL_CASE_REGEX = "([a-z]+)([A-Z]+)"

    val SNAKE_CASE_PATTERN = "$1\\_$2"

    override fun toPhysicalCatalogName(name: Identifier?, context: JdbcEnvironment?): Identifier? {
        return super.toPhysicalCatalogName(name, context)
    }

    override fun toPhysicalSchemaName(name: Identifier?, context: JdbcEnvironment?): Identifier? {
        return super.toPhysicalSchemaName(name, context)
    }

    override fun toPhysicalTableName(name: Identifier?, context: JdbcEnvironment?): Identifier? {
        return super.toPhysicalTableName(name, context)
    }

    override fun toPhysicalSequenceName(name: Identifier?, context: JdbcEnvironment?): Identifier? {
        return formatIdentifier(super.toPhysicalSequenceName(name, context))
    }

    override fun toPhysicalColumnName(name: Identifier?, context: JdbcEnvironment?): Identifier? {
        return formatIdentifier(super.toPhysicalColumnName(name, context))
    }

    private fun formatIdentifier(identifier: Identifier?): Identifier? {
        if (identifier != null) {
            val name = identifier.text

            val formattedName = name.replace(CAMEL_CASE_REGEX.toRegex(), SNAKE_CASE_PATTERN).toLowerCase()

            return if (formattedName != name)
                Identifier.toIdentifier(formattedName, identifier.isQuoted)
            else
                identifier
        } else {
            return null
        }

    }
}
