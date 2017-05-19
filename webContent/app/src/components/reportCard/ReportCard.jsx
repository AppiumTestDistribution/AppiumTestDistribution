import React from 'react';
import { Card, CardHeader, CardTitle } from 'material-ui/Card';
import { Col } from 'react-flexbox-grid';

const styles = {
  card: {
    margin: 20
  },
  imageWrapper: {
    textAlign: 'center',
  },
  image: {
    width: '80%',
  }
}

const ReportCard = ({ content }) => {
  let screenShotPath = content.screenShot.split('/target')[1]
  console.log(content)
  return (
    <Col sm={12} md={6} lg={4}>
      <Card style={styles.card} >
        <CardTitle title={content.methodName} subtitle={`Device UUId: ${content.info.deviceUDID}`} 
           subtitle={`Model: ${content.info.deviceModel}`}/>
        <CardHeader
          titleColor={'#444444'}
          title={`Device OS:  ${content.info.deviceName}, Verion: ${content.info.deviceOS}`}
          subtitle={`Device UUId: ${content.info.deviceUDID}`}
         
        />
        <div style={styles.imageWrapper}>
          <img style={styles.image}src={screenShotPath} alt="screenshot" />
        </div>
      </Card>
    </Col>
  )
};



export default ReportCard;

