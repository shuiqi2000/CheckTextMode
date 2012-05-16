; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "CheckTextMode"
#define MyAppVersion "1.0"
#define MyAppPublisher "Ji Fang"
#define MyAppExeName "CheckTextMode.exe"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{ED70BFD3-6526-43A9-AE98-0A07E471C187}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
AllowNoIcons=yes
OutputDir=E:\CheckTextMode\pkg
OutputBaseFilename=setup
SetupIconFile=E:\CheckTextMode\swallow.ico
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "E:\CheckTextMode\CheckTextMode.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "E:\CheckTextMode\README"; DestDir: "{app}"; Flags: ignoreversion
Source: "E:\CheckTextMode\lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion recursesubdirs createallsubdirs

; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"

