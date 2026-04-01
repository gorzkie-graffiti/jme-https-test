# J2ME HTTPS Connection Test

This project is a simple J2ME application (MIDlet) used to test if a legacy mobile device (like an old Sony Ericsson) can successfully establish a direct HTTPS connection to a modern web domain.

Since modern APIs and websites enforce strict, updated SSL/TLS certificates that many legacy Java 2 Micro Edition (J2ME) devices do not support, this application helps diagnose whether your device can natively handle modern certificates or if it requires a proxy/bypass.

## How to use

1.  **Download the App**: Go to the **Releases** tab and download `jme-https-test.jar` and `TestMIDlet.jad`.
2.  **Install on Phone**: Transfer both files to your phone's storage.
3.  **Run the Test**: Open the app and click "Testuj". It will attempt to connect to the pre-configured URL and display the response or the specific error code (e.g., `8000001b` for SSL handshake failure on Sony Ericsson).

---

## Modifying and Building

You can clone this repository to test any domain you wish.

### 1. Change the Target URL
Open [TestMIDlet.java](src/TestMIDlet.java) and modify the `url` variable on line 39:

```java
String url = "https://your-custom-domain.com/";
```

### 2. Build the Application
Ensure you have a Java JDK installed. You don't need a heavy J2ME SDK; the build command below will download the necessary compiler tools and libraries automatically into a `.tools` folder.

Run the following command in PowerShell from the project root:

```powershell
# 1. Setup Build Tools
mkdir .tools -ErrorAction SilentlyContinue
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/eclipse/jdt/ecj/3.33.0/ecj-3.33.0.jar" -OutFile ".tools\ecj.jar"
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/microemu/cldcapi11/2.0.4/cldcapi11-2.0.4.jar" -OutFile ".tools\cldcapi11.jar"
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/microemu/midpapi20/2.0.4/midpapi20-2.0.4.jar" -OutFile ".tools\midpapi20.jar"
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/com/guardsquare/proguard-base/7.1.1/proguard-base-7.1.1.jar" -OutFile ".tools\proguard.jar"
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/com/guardsquare/proguard-core/7.1.1/proguard-core-7.1.1.jar" -OutFile ".tools\proguard-core.jar"

# 2. Compile
java -jar .tools\ecj.jar -1.4 -source 1.4 -target 1.4 -bootclasspath ".tools\cldcapi11.jar;.tools\midpapi20.jar" src\TestMIDlet.java

# 3. Package and Preverify
jar cvfm raw.jar manifest.txt -C src/ .
java -cp ".tools\proguard.jar;.tools\proguard-core.jar" proguard.ProGuard -injars raw.jar -outjars releases\jme-https-test.jar -libraryjars ".tools\cldcapi11.jar" -libraryjars ".tools\midpapi20.jar" -dontobfuscate -dontoptimize -dontshrink -microedition -force

# 4. Generate JAD
$size = (Get-Item releases\jme-https-test.jar).Length
$manifest = Get-Content manifest.txt
$manifest += "MIDlet-Jar-Size: $size"
$manifest += "MIDlet-Jar-URL: jme-https-test.jar"
$manifest | Out-File -Encoding UTF8 releases\TestMIDlet.jad

# 5. Cleanup
Remove-Item -Force raw.jar
```
