'use strict';

import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Image,
  NativeModules
} from 'react-native';

let title = 'React Native界面';

const KONGZHONG_RNJNI  = NativeModules.KONGZHONG_RNJNI;

class HelloWorld extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isSpeaker: true,
            isMute: false
        };
    }



  //****************微信相关接口****************
  initWX(){
	//初始化微信SDK
	KONGZHONG_RNJNI.initWX("wx8596186a3fdc9f77");
  }

  login(){
	//微信登录
	KONGZHONG_RNJNI.login();
  }

  //****************声网SDK相关接口****************
  initAgora(){
	//初始化声网SDK
	KONGZHONG_RNJNI.init("324cbe8505a649bb9f157e6946fa832b");
  }

  joinChannel(){
	//加入频道
	KONGZHONG_RNJNI.joinChannel(null, "voiceDemoChannel1", "Extra Optional Data", 0);
  }

  leaveChannel(){
	//离开频道
	KONGZHONG_RNJNI.leaveChannel();
  }

  onSwitchSpeakerphone(){
    this.setState({
        isSpeaker: !this.state.isSpeaker
     }, () => {
	    //打开外放
	    KONGZHONG_RNJNI.onSwitchSpeakerphone(this.state.isSpeaker);
    });
  }

  onSwitchMicrophone(){
    this.setState({
        isMute: !this.state.isMute
     }, () => {
	    //将自己静音
	    KONGZHONG_RNJNI.onSwitchMicrophone(this.state.isMute);
    });
  }

  startVoiceActivity(){ //打开语音通话Activity
    //console.log("MODULE NAME: ",KONGZHONG_RNJNI.NATIVE_MODULE_NAME);
	//这里的KONGZHONG_RNJNI 原生模块中getName()方法返回的字符串
	KONGZHONG_RNJNI.startActivity("Voice");
  }
  startVideoActivity(){ //打开视频通话Activity
    //console.log("MODULE NAME: ",KONGZHONG_RNJNI.NATIVE_MODULE_NAME);
	//这里的KONGZHONG_RNJNI 原生模块中getName()方法返回的字符串
	KONGZHONG_RNJNI.startActivity("Video");
  }
  showToast(){
    //console.log("MODULE NAME: ",KONGZHONG_RNJNI.NATIVE_MODULE_NAME);

	//这里的KONGZHONG_RNJNI 原生模块中getName()方法返回的字符串
	KONGZHONG_RNJNI.showToast("ReactNative调用Android接口", KONGZHONG_RNJNI.LONG);
  }



  render() {
    const {isMute, isSpeaker} = this.state;

    return (
      <View style={styles.container}>
         <Image source={require('./images/kongzhong.png')} />

         <Text style={styles.welcome} >
            {title}
         </Text>



         <Text style={styles.welcome} onPress={this.initWX.bind(this)}>
            初始化微信SDK
         </Text>
         <Text style={styles.welcome} onPress={this.login.bind(this)}>
            微信登录
         </Text>

         <Text style={styles.welcome} onPress={this.initAgora.bind(this)}>
            初始化声网SDK
         </Text>
         <Text style={styles.welcome} onPress={this.joinChannel.bind(this)}>
            加入频道
         </Text>
         <Text style={styles.welcome} onPress={this.leaveChannel.bind(this)}>
            离开频道
         </Text>
         <Text style={styles.welcome} onPress={this.onSwitchSpeakerphone.bind(this)}>
            扬声器开关
         </Text>
         <Text style={styles.welcome} onPress={this.onSwitchMicrophone.bind(this)}>
            麦克风开关
         </Text>

         <Text style={styles.welcome} onPress={this.startVoiceActivity.bind(this)}>
            打开语音通话Activity
         </Text>
         <Text style={styles.welcome} onPress={this.startVideoActivity.bind(this)}>
            打开视频通话Activity
         </Text>

      </View>
    );
  } 
  
}



const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  }
});

AppRegistry.registerComponent('ReactNative_JNITest', () => HelloWorld);