import { Route, Switch, Redirect } from "react-router-dom";
import  routes from "./../routes";
import {connect} from "react-redux";
const Body = (props) => {
	const {isLoggedIn} = props;
	return (
		<div className="main-content">
			<div className="page-content">
				<div className="container-fluid">
					<div className="page-content">
						<div className="container-fluid">
							{ 
							<Switch>
								{!isLoggedIn ? (<Redirect to="/login"/>) : (routes.map((route,index)=>{
										return route.component ? (
											<Route
											key={index}
											path={route.path}
											exact={route.exact}
											component={route.component}
											/>
										): null
									}))}
							</Switch>
							}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};
const mapStateToProps = state =>({
    isLoggedIn: state.auth.isLoggedIn
});
export default connect(mapStateToProps)(Body);
