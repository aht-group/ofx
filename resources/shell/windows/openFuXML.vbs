Set WshShell = WScript.CreateObject( "WScript.Shell" )
Set fso     = WScript.CreateObject("Scripting.FileSystemObject") 
If fso.FileExists("simple-client.prop") = False Then WshShell.Run "init.vbs",0,True
WshShell.Run "client.bat",0,True