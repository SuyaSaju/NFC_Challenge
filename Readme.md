## Demo Application

Demo Video Link : 
https://photos.app.goo.gl/Apx8JyWF8MQnrisV9

Download Link: https://drive.google.com/drive/folders/17gZbv4UP9csuaKXIbWXfJ0VzASXiFOcM?usp=sharing

- We have built two applications:
- Using Android Host-based card emulation, we have built a NFC card emulator which will mimic the Loyalty Payment Card (loyalty-card.apk)
- The other will act as a card reader which mimics the POS terminal (POS terminal.apk)

## High level overview of the approach taken in the POC

- User opens the app and enters their card details in the screen.

- This card details has to be encrypted before storing this info in the shared preferences. But we are NOT using a plain hardcoded key to encrypt the card details which can be easily hacked. Instead, we add an extra layer of security as explained in the Tech details section(use random generated AES key for versions > Android M and use a combination of RSA public/private key along with AES for Android versions lesser than M). 
    
- Now that the user's card detail is saved securely, let's tap the card emulator at the POS terminal. As soon as this is done, a APDU(Application Protocol Data Unit) command is sent from the POS terminal to the card emulator.

- This triggers the HCE service that we have written in the card emulator using AID (application id) This AID has to be registered so that the reader can contact the right HCE service.This service retrieves the key and the encrypted card details from the preferences and using this key, decrypts to find the original card details (as specified in Tech details section). 

- Now using this actual card details, the payment can be processed.

## Tech details for storing card details securely

Since we have to support Android versions from 19, the implementation for this has to be handled as two different cases:

- Android M and beyond
	- We make use of the keystore api to generate random AES key when the app is launched for the first time. 
	- To store card details, we retrieve this random key from the keystore, encrypt the card details with this key using the AES algorithm and store the encrypted card details in the preferences.
	- To retrieve card details, we fetch the encrypted data from the preferences and the key from the keystore, and we use this key to decrypt the card details. 

- Versions lesser than Android M
	- The key generator is not available until after API 23. So we would have to generate our own random keys using the KeyPairGeneratorSpec API. We generate a public/private key value pair (RSA) using this API.
	- We generate another random AES key
	- We encrypt the AES key using the RSA public key
	- We store this encrypted AES key in preferences, the first time when we launch the app. 
	- To encrypt card info, we fetch this saved encrypted AES key from the preferences and first decrypt the key using the private RSA key. With this decrypted key, we encrypt the card details and store it in preferences.
	- To decrypt card info, we retrieve the encrypted AES key and also the encrypted card info from the preferences. First using the private RSA key, we decrypt the AES key. Using this, we decrypt the encrypted card info. 

This approach is more secure rather than relying on the Android Application Sandbox as the key is randomly generated and securely managed. Also, we have added BIND_NFC_SERVICE permission to the service that we have written which means that only the OS can communicate with our HCE service. 

 
