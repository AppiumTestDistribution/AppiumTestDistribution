import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import MainScreen from './containers/MainScreen.jsx'
class App extends Component {
  render() {
    return (
      <MuiThemeProvider>
        <MainScreen/>
      </MuiThemeProvider>
    );
  }
}

export default App;
