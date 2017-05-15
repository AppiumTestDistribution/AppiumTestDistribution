import React, { Component } from 'react';
import Header from '../components/header/Header.jsx'
import Drawer from '../components/drawer/Drawer.jsx'
import ReportScreen from './ReportScreen.jsx'
import { Row, Col } from 'react-flexbox-grid';
import data from '../data/MockData.json'

import injectTapEventPlugin from 'react-tap-event-plugin'

injectTapEventPlugin();


class MainScreen extends Component {
    constructor(props) {
        super(props)
        this.handleReportContent = this.handleReportContent.bind(this)
        this.state = {
            isDrawerActive: true,
            item: []
        }
    }
    componentWillMount() {
        this.setState({item:data[0]})
    }

    handleDrawerState = () => this.setState({ isDrawerActive: !this.state.isDrawerActive })
 
    handleReportContent(item) {
        this.setState({item})
    }


    render() {
        return (
            <div>
                <Header handleDrawerVisibility={this.handleDrawerState} />
                <Row>
                    <Col sm={2}>
                        <Drawer open={this.state.isDrawerActive}
                            handleReportContent={(item)=>this.handleReportContent(item)}
                            content={data} />
                    </Col>
                    <Col  sm={10}>
                        <ReportScreen testMethods={this.state.item.testMethods}/>
                    </Col>
                </Row>
            </div>
        )
    }
}

export default MainScreen