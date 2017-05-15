import React, { Component } from 'react';
import ReportCard from '../components/reportCard/ReportCard.jsx'

const styles = {
    reportCardContainer: {
        display: 'flex',
        flexWrap: 'wrap',
        marginTop: '70px'
    }
}

class ReportScreen extends Component {

    render() {
        return (
            <div style={styles.reportCardContainer}>
                {this.props.testMethods.map((method, index) => {
                    return (
                        <ReportCard key={index} content={method} />
                    )
                })}
            </div>
        )
    }
}

export default ReportScreen