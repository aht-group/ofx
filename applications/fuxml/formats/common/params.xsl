<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!-- 
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
	  -->	
	<xsl:param name="documentname">document</xsl:param>
	<xsl:param name="projectname"></xsl:param>
	<!--xsl:param name="workdir">file:///I:/output/k20022/latexpdf/oop_kurs</xsl:param-->
	<!--xsl:param name="workdir">file:///I:/output/k20026/latexpdf/pk_kurs</xsl:param-->
	<!--xsl:param name="workdir">file:///I:/output/orecht_kurse/latexpdf/k05366</xsl:param-->
	<!--xsl:param name="workdir">file:///I:/output/wi_kurse/latexpdf/k00813</xsl:param-->
	<!--xsl:param name="workdir">file:///I:/output/ip_kurse/latexpdf/k34661</xsl:param-->
	<!--xsl:param name="workdir">file:///I:/output/testprojekt_ks/latexpdf/bilder</xsl:param-->
	<!--xsl:param name="workdir">file:///I:/output/zfepub/latexpdf/zfetest30</xsl:param-->
	<xsl:param name="workdir">file:///I:/output/testprojekt_ks/latexpdf/zfetest</xsl:param>
	<!--xsl:param name="workdir">file:///I:/output/testprojekt_ks/latexpdf/tabelletest</xsl:param-->
	<!--xsl:param name="workdir">file:///I:/output/lgks_krypto/latexpdf/crypto2_kurs</xsl:param-->
   	<xsl:variable name="config" select="document('config.xml')"/>
	<xsl:variable name="styles" select="$config/config/styles"/>
</xsl:stylesheet>
