import React from 'react';
import logo from '../../logo.svg';
import AppBar from 'material-ui/AppBar';
import './Header.css'

const Header = ({handleDrawerVisibility}) => {
    return (
        <AppBar
            style={{position: 'fixed'}}
            title="Appium Test Distribution Reporter"
            iconElementRight={<img src={logo} className="App-logo" alt="logo" />}
            onLeftIconButtonTouchTap={()=>{handleDrawerVisibility()}}
        />
        // <div className="App-header">
        //     <img src={logo} className="App-logo" alt="logo" />
        //     <h2>Appium Test Distribution Reporter</h2>
        // </div>
    )
}

export default Header