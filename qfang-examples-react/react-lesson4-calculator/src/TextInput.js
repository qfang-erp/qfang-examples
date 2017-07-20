import React from 'react';

class TextInput extends React.Component {
	componentDidMount() {
		const myText = this.refs.myText;
	}
	
	render() {
		return(<input type="text" name={this.props.name} ref="myText" />);
	}
}


export default TextInput;