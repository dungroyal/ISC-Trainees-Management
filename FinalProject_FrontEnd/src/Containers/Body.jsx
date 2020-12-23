import { Route, Switch } from "react-router-dom";
import  routes from "./../routes"
const Body = () => {
	return (
		<div className="main-content">
			<div className="page-content">
				<div className="container-fluid">
					<div className="page-content">
						<div className="container-fluid">
							{ 
							<Switch>
								{
									routes.map((route,index)=>{
										return route.component ? (
											<Route
											key={index}
											path={route.path}
											exact={route.exact}
											component={route.component}
											/>
										): null
									})}
							</Switch>

							}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default Body;
