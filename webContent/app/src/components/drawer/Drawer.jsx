import React from 'react';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';


const styles = {
  drawerConatiner: {
    marginTop: 65
  }
}

export default class AppDrawer extends React.Component {

  handlemenuClick = (item) => {
    this.props.handleReportContent(item)
  }

  render() {
    return (
      <Drawer open={this.props.open}
        containerStyle={styles.drawerConatiner}>
        {this.props.content.map((item, index) => {
          return (
            <MenuItem key={index} onTouchTap={() => { this.handlemenuClick(item) }}>{item.name}</MenuItem>
          )
        })}
      </Drawer>
    );
  }
}
