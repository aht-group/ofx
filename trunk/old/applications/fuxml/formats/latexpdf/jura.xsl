<?xml version="1.0"?>
<!-- ********************************************************************
	$Id: jura.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

		<xsl:template match="fall|kommentar|norm|rechtsprechung">
		<xsl:call-template name="labelbox">
			<xsl:with-param name="style" select="$styles/entry[@name=name(current())]"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="zwischentitel[parent::fall or parent::kommentar or parent::norm or parent::rechtsprechung]"><xsl:apply-templates/></xsl:template>
</xsl:stylesheet>

