环境变量 SCRCPY_SERVER_PATH
D:\work_kt\scrcpy\server\build\outputs\apk\debug\server-debug.apk
client会自动安装SCRCPY_SERVER_PATH指定的这个apk

//手动启动server
adb shell CLASSPATH=/data/local/tmp/scrcpy-server app_process / com.genymobile.scrcpy.Server 3.0.2
