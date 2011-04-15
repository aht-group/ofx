<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: technik.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
	********************************************************************
	*******************************************************************************
	| openFuXML open source                                                       |
	*******************************************************************************
	| Copyright (c) 2002-2006 openFuXML open source, University of Hagen          |
	|                                                                             |
	| This program is free software; you can redistribute it and/or               |
	| modify it under the terms of the GNU General Public License                 |
	| as published by the Free Software Foundation; either version 2              |
	| of the License, or (at your option) any later version.                      |
	|                                                                             |
	| This program is distributed in the hope that it will be useful,             |
	| but WITHOUT ANY WARRANTY; without even the implied warranty of              |
	| MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               |
	| GNU General Public License for more details.                                |
	|                                                                             |
	| You should have received a copy of the GNU General Public License           |
	| along with this program; if not, write to the Free Software                 |
	| Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. |
	*******************************************************************************
	******************************************************************** -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="buchstaeblich"><xsl:param name="mode"/><xsl:call-template name="nobox">
		<xsl:with-param name="style" select="$styles/entry[@name='buchstaeblich']"/>
		<xsl:with-param name="mode" select="$mode"/>
	</xsl:call-template>
<!--\begin{footnotesize}\begin{alltt}\normalfont{}<xsl:apply-templates/>\end{alltt}\end{footnotesize}--></xsl:template>

<xsl:template match="proglist">
	<xsl:param name="mode"/>
	<xsl:call-template name="genericbox">
		<xsl:with-param name="style" select="$styles/entry[@name='proglist']"/>
		<xsl:with-param name="mode" select="$mode"/>
	</xsl:call-template>

	<!--\begin{Listing}
		<xsl:apply-templates/>
		\caption{{<xsl:apply-templates select="zwischentitel" mode="call"/>}}
	\end{Listing}-->
</xsl:template>

<xsl:template match="schreibmaschine[parent::buchstaeblich]">{\tt{}<xsl:apply-templates/>}</xsl:template>


<xsl:template match="zwischentitel[parent::proglist]"><xsl:apply-templates/></xsl:template> 

</xsl:stylesheet>