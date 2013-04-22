package me.longerian.abc.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class SecureOrderTaker {

	public final static int DEFAULT_PORT = 7000;
	public final static String algorithm = "SSL";
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String content = "hello";
		String header = "HTTP/1.1 200 OK\r\n"
				+ "Server: SecureOrderTaker\r\n"
				+ "Content-Length: " + content.getBytes().length + "\r\n"
				+ "Content-Type: text/html\r\n"
				+ "\r\n";
		try {
			SSLContext context = SSLContext.getInstance(algorithm);
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			char[] password = "2andnotafnord".toCharArray();
			ks.load(new FileInputStream("jnp3e.keys"), password);
			kmf.init(ks, password);
			context.init(kmf.getKeyManagers(), null, null);
			SSLServerSocketFactory factory = context.getServerSocketFactory();
			SSLServerSocket server = (SSLServerSocket) factory.createServerSocket(DEFAULT_PORT);
			String[] supported = server.getSupportedCipherSuites();
			String[] anonCipherSuitesSupported = new String[supported.length];
			int numAnonCipherSuitesSupported = 0;
			for(int i = 0; i < supported.length; i++) {
				if(supported[i].indexOf("_anon_") > 0) {
					anonCipherSuitesSupported[numAnonCipherSuitesSupported++] = supported[i];
				}
			}
			String[] oldEnabled = server.getEnabledCipherSuites();
			String[] newEnabled = new String[oldEnabled.length + numAnonCipherSuitesSupported];
			System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
			System.arraycopy(anonCipherSuitesSupported, 0, newEnabled, oldEnabled.length, numAnonCipherSuitesSupported);
			server.setEnabledCipherSuites(newEnabled);
			Socket theConnection = null;
			try {
				while(true) {
					theConnection = server.accept();
					InputStream in = theConnection.getInputStream();
					int c;
					while((c = in.read()) != -1) {
						System.out.write(c);
					}
					OutputStream os = new BufferedOutputStream(theConnection.getOutputStream());
					os.write(header.getBytes("ASCII"));
					os.write(content.getBytes());
					os.flush();
				}
			} catch(IOException ex) {
				ex.printStackTrace();
			} finally {
				if(theConnection != null) {
					theConnection.close();
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch(KeyManagementException ex) {
			ex.printStackTrace();
		} catch(KeyStoreException ex) {
			ex.printStackTrace();
		} catch(NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch(CertificateException ex) {
			ex.printStackTrace();
		} catch(UnrecoverableKeyException ex) {
			ex.printStackTrace();
		}
	}

}
