import React from 'react';

class Select extends React.Component {
	constructor(props) {
		super(props);
		this.handleChange = this.handleChange.bind(this);
		this.result = {value: 0};
	}
	handleChange(e) {
		e.preventDefault();
		
		// 因为不知道如何和其他 React 组件交互，这里没有更好的方法获取 TextInput React 组件
		// 所以只能采用原生js的方法获取 input DOM 对象
		var x = Number(document.getElementsByName("x")[0].value);
		var y = Number(document.getElementsByName("y")[0].value);
		
		var options = this.selectDom.value;  // this.selectDom 获取当前 select 的 DOM 元素
		if(options == 1) {
			this.result.value = x+y;
		} else if(options == 2) {
			this.result.value = x-y;
		} else if(options == 3) {
			this.result.value = x*y;
		} else if(options == 4) {
			this.result.value = x/y;
		}
		document.getElementsByName("z")[0].value = this.result.value;
	}
	render() {
		return (
			<select name={this.props.name} ref={(ref)=>this.selectDom=ref} onChange={this.handleChange}>
				<option value="">-请选择-</option>
				<option value="1"> + </option>
				<option value="2"> - </option>
				<option value="3"> * </option>
				<option value="4"> / </option>
			</select>		
		)
	}
}

export default Select;