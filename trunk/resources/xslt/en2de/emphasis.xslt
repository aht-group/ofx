<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org/ofx"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="ofx:emphasis/@style"/>
	
	<xsl:template match="ofx:emphasis/@italic"/>
	<xsl:template match="ofx:emphasis/@bold"/>
	
	<xsl:template match="ofx:emphasis[@style='typewriter']">
		<xsl:element name="schreibmaschine"><xsl:apply-templates select="@*|node()"/></xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:emphasis[@style='normal']">
		<xsl:if test="@*">
			<xsl:for-each select="@*">
				<xsl:element name="{name()}">
					<xsl:value-of select="." />
				</xsl:element>
			</xsl:for-each>
		</xsl:if>
		<xsl:apply-templates />
	</xsl:template>
	
</xsl:stylesheet>