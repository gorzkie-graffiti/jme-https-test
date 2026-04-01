# J2ME HTTPS Proxy Test

This project demonstrates a simple J2ME application (MIDlet) capable of connecting to modern websites and APIs by proxying requests through a Cloudflare Worker.

Since modern APIs and websites enforce strict, updated SSL/TLS certificates that legacy Java 2 Micro Edition (J2ME) devices (such as old Sony Ericsson phones) do not support, this tool effectively routes the connection through Cloudflare so they can connect correctly!

## Architecture

1. **J2ME App (`src/TestMIDlet.java`)**: Runs on the legacy phone. Sends a simple HTTP/HTTPS GET request to the Cloudflare Worker.
2. **Cloudflare Worker (`worker.js`)**: Runs on modern Cloudflare infrastructure. Resolves the modern certificates and serves the data over a legacy-friendly connection.

## How to used

### 1. Cloudflare Worker Deployment
To host the proxy server yourself:
1. Make sure Node.js is installed.
2. Run `npx wrangler deploy` in the root folder of this project in your terminal.
3. It will install wrangler, log you in, and automatically host your proxy worker.

### 2. J2ME Setup (Mobile Phone)
The easiest way to install the app on your mobile phone is by downloading the `.jar` and `.jad` files found in the **Releases** tab of this repository.

1. Download the `jme-https-test.jar` and `TestMIDlet.jad`.
2. Connect your legacy phone (e.g., via USB cable or Bluetooth).
3. Transfer both files to the phone's storage.
4. Open the file manager on your phone and open the `.jad` or `.jar` file to install the application.

*Note: The code is written specifically using older Java 1.4 syntax and has been pre-verified using ProGuard so that it works seamlessly on devices like Sony Ericsson without throwing "VerifyError".*
