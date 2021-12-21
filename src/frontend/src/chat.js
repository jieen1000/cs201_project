import React, {Component} from 'react';


/**
 * {@code KommunicateChat} links chatbot to the bottom of the page
 *
 * @author Chong Zhan Han
 * @version 1.0
 * @since 2021-10-27
 */

class KommunicateChat extends Component{
    constructor(props){
        super(props);
    }


    componentDidMount(){

        (function(d, m){
            var kommunicateSettings = {"appId":"1326488dc77f91039518354a26d80239f","popupWidget":true,"automaticChatOpenOnNavigation":true};
            var s = document.createElement("script"); s.type = "text/javascript"; s.async = true;
            s.src = "https://widget.kommunicate.io/v2/kommunicate.app";
            var h = document.getElementsByTagName("head")[0]; h.appendChild(s);
            window.kommunicate = m; m._globals = kommunicateSettings;
            })(document, window.kommunicate || {});
        
    }

    render(){
        return (
            <div></div>
        )
    }
}

export default KommunicateChat;
