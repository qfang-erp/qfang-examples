import React from 'react';
import TextInput from './TextInput';
import Select from './Select';

class App extends React.Component {
	constructor(props) {
		super(props);
	}
	render() {
		return (
			<div>
				<TextInput name="x" />&nbsp;
				<Select name="add" />&nbsp;
				<TextInput name="y" />
				=
				<TextInput name="z" />
			</div>
		)
	}
}

export default App;
