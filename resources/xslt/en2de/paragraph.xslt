<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="paragraph">
		<xsl:element name="absatz">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="paragraph/@top"/>
	<xsl:template match="paragraph[@top='normal']">
		<xsl:element name="absatz"><xsl:apply-templates select="@*|node()"/></xsl:element>
	</xsl:template>
	<xsl:template match="paragraph[@top='small']">
		<xsl:element name="absatz-klein"><xsl:apply-templates select="@*|node()"/></xsl:element>
	</xsl:template>
	<xsl:template match="paragraph[@top='mini']">
		<xsl:element name="absatz-mini"><xsl:apply-templates select="@*|node()"/></xsl:element>
	</xsl:template>
	<xsl:template match="paragraph[@top='zero']">
		<xsl:element name="absatz-ohne"><xsl:apply-templates select="@*|node()"/></xsl:element>
	</xsl:template>

</xsl:stylesheet>