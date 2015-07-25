Android Sikuli Automation Framework (Developed by Artur Spirin) supports CI with Jenkins. It is written in Java and powered by Sikuli API for UI interaction. It offers support for CI for server side as well as for functional/regression testing.
Repository: secret Stash URL goes here :)

Getting Started:

	Setting up testing environment
		1. Download & Install Java JDK
		2. Set JAVA_HOME in the System Environment Variables & point it to the JDK
		3. Download & Install Android SDK
		4. Download & Install platform-tools via Android SDK Manager
		5. Add path to adb.exe inside the platform-tools directory to the System Environment Variable
		6. Create C:\Auto\Local directory
		7. Download Android Sikuli Automation Framework v2 and place it in C:\Auto\Local directory
		8. Go to C:\Auto\Local\Android Sikuli Automation Framework v2\Libs, find Sikulix folder
		9. Move it to C:\Users\yourUserName\AppData\Roaming
		10. Download & Install Mobizen client

	Setting up Jenkins CI
		1. To set up new Jenkins nodes refer to  How to work with Jenkins
		2. Once you have a node set up, create your Jenkins project
		3. Navigate to the project you created and  Add Step to Execute Windows Batch command
		4. Enter the following command: cmd /k call "C:\Auto\Local\Android Sikuli Automation Framework v2\theRedButton.bat"

	Connecting devices. Currently only specific devices are supported: Moto X2, Moto X, and Galaxy S3.
		1. Launch Mobizen on VM/Host
		2. Download Mobizen on mobile device
		3. Connect device via USB cable to the host machine
		4. Set up settings for VM to route the USB device directly to VM when it is connected
		5. Run "adb devices" command to make sure device is connected properly
		6. Login to Mobizen with username: mockUp & password: mockUp
		7. Connect desired device

	Triggering tests.
		1. Send mockUp request:
			- to trigger server tests: http://mockUp/mockUp_request.php?host=mockUp&type=ANDROID_SRV&protocol=mockUp
				# where host= enter your desired hss host that you would like to test
				# where protocol= enter desired mode (mockUp or ovpn)
				# where type= it will always be ANDROID_SRV
			- to trigger functional tests: http://mockUp/mockUp_request.php?type=ANDROID_FUN&notes=v359_release_google_05-27-15.apk
				# where type= it will always be ANDROID_FUN
				# where notes= put the full apk name including extension
		2. Jenkins will pick up the mockUp request and trigger the job on the slave
		3. While tests are running results will upload to QA DB in real time and they will be available for viewing at http://mockUp/mockUp.php?req=<mockUpRequestId>#