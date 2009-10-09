<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <xsl:apply-templates select="*"/>
  </xsl:template>
  <xsl:template match="node()">
    <xsl:copy><xsl:apply-templates select="node()"/></xsl:copy>
  </xsl:template>
  <xsl:template match="paragraph">
    <absatz><xsl:apply-templates select="node()"/></absatz>
  </xsl:template>
  <xsl:template match="section">
    <abschnitt><xsl:apply-templates select="node()"/></abschnitt>
  </xsl:template> 
</xsl:stylesheet>