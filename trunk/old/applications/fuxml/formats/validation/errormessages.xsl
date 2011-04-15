<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1" indent="yes"/>
	<!-- XREF cannot be resolved -->
	<xsl:variable name="XREFNOTFOUND">Verweis kann nicht aufgel�st werden!</xsl:variable>
	<xsl:variable name="XREFHINT">�berpr�fen Sie das Attribut "zielmarke". Hat das "id" Attribut des referenzierten Elements den gleichen Wert wie "zielmarke"?</xsl:variable>
	
	<!-- No range values for courseunit text -->
	<xsl:variable name="CUNORANGE">Die Bereichsangaben f�r den Kurseinheitslehrtext fehlen!</xsl:variable>
	<xsl:variable name="CUNORANGEHINT">Kennzeichnen Sie den Beginn und das Ende der Kurseinheit im "ke-lehrtext" Element durch Angabe der entsprechenden ID's!</xsl:variable>

	<!--Fauly CU  range, ID @bereich-start and @bereich-ende cannot be resolved -->
	<xsl:variable name="CURANGENOTFOUND">Die Bereichsangaben f�r die Kurseinheit k�nnen nicht aufgel�st werden.</xsl:variable>
	<xsl:variable name="CURANGENOTFOUNDHINT">�berpr�fen Sie die Attribute "bereich-start" und "bereich-ende". Gibt es zu diesen Attributen ein Element mit gleichlautender "id" im "lehrtext"?</xsl:variable>
	
	<!-- CU end before CU start-->
	<xsl:variable name="CUENDBEFORESTART">Die Bereichsangaben f�r die Kurseinheit sind fehlerhaft! Der Bereichsanfang muss vor dem Bereichsende kommen.</xsl:variable>
	<xsl:variable name="CUENDBEFORESTARTHINT">Korrigieren Sie die Angaben in den Attributen "bereich-anfang" und "bereich-ende"</xsl:variable>
	
	<!-- Latex Syntax contains '$' character -->
	<xsl:variable name="DOLLARINLATEXSYNTAX">Die LaTeX-Formel enth�lt ein Dollar-Zeichen!</xsl:variable>
	<xsl:variable name="DOLLARINLATEXSYNTAXHINT">Wenn das Zeichen nicht in der Form '\$' auftritt sollte es entfernt werden, da es ansonsten zu einer fehlerhaften Ausgabe kommen kann.</xsl:variable>
	
	<!-- Latex error -->
	<xsl:variable name="ERRORINLATEXSYNTAXHINT">Fehlerhafter Latex Syntax.</xsl:variable>
	
	<!-- Missing ke-lehrtext element -->
	<xsl:variable name="NOCUDEFINED">In der Kurseinheit fehlt das Element "ke-lehrtext"!</xsl:variable>
	<xsl:variable name="NOCUDEFINEDHINT">Wenn das Element "ke-lehrtext" nicht vorhanden werden nur die Vorspannelemente ausgegeben. Sie sollten das Element "ke-lehrtext" einf�gen und mit entsprechenden Bereichsangaben versehen.</xsl:variable>
	
	<!-- Double ID in -->
	<xsl:variable name="DOUBLEID">Doppelte ID</xsl:variable>
	<xsl:variable name="DOUBLEIDHINT">Sie haben eine ID doppelt vergeben. Dies kann zu Problemen bei der Ausgabe f�hren. Sie sollten die doppelte ID umbenennen.</xsl:variable>

	<!-- Table Colspec without @colnum or @colname -->
	<xsl:variable name="COLSPECERROR">Fehlerhaftes Tabellenformat</xsl:variable>
	<xsl:variable name="COLSPECERRORHINT">Die "colspec" Elemente innerhalb einer Tabelle m�ssen das Attribut "colnum" aufweisen.</xsl:variable>
	
	<!-- tgroup without colspec -->
	<xsl:variable name="COLSPECMISSINGERROR">Fehlerhaftes Tabellenformat</xsl:variable>
	<xsl:variable name="COLSPECMISSINGERRORHINT">Die Tabelle enth�lt keine "colspec" Elemente.</xsl:variable>


</xsl:stylesheet>
