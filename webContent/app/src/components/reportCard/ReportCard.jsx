import React from 'react';
import { Card, CardHeader, CardTitle, CardText } from 'material-ui/Card';
import { Col } from 'react-flexbox-grid';

const styles = {
  card: {
    margin: 20
  },
  image: {
    textAlign: 'center'
  }
}

const ReportCard = ({ content }) => {
  let screenShotPath = content.screenShot.split('/target')[1]
  return (
    <Col sm={12} md={6} lg={4}>
      <Card style={styles.card}>
        <CardHeader
          title={content.info.deviceName}
          subtitle={content.info.deviceModel}
        />
        <div style={styles.image}>
          <img src={screenShotPath} alt="screenshot" />
        </div>
        <CardTitle title="Card title" subtitle="Card subtitle" />
        <CardText>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        Donec mattis pretium massa. Aliquam erat volutpat. Nulla facilisi.
        Donec vulputate interdum sollicitudin. Nunc lacinia auctor quam sed pellentesque.
        Aliquam dui mauris, mattis quis lacus id, pellentesque lobortis odio.
      </CardText>
      </Card>
    </Col>
  )
};



export default ReportCard;

