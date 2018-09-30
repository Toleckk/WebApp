import 'react-toastify/dist/ReactToastify.css';
import './app.scss';

import React from 'react';
import { connect } from 'react-redux';
import { HashRouter as Router } from 'react-router-dom';
import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { getProfile } from 'app/shared/reducers/application-profile';
import Header from 'app/shared/layout/header/header';
import Footer from 'app/shared/layout/footer/footer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { AUTHORITIES } from 'app/config/constants';
import AppRoutes from 'app/routes';
import { Loader } from 'semantic-ui-react';

export interface IAppProps extends StateProps, DispatchProps {}

export class App extends React.Component<IAppProps> {
  componentDidMount() {
    this.props.getSession();
    this.props.getProfile();
  }

  loading() {
    return (
      (localStorage.hasOwnProperty('jhi-authenticationToken') || sessionStorage.hasOwnProperty('jhi-authenticationToken')) &&
      this.props.isLoading
    );
  }

  render() {
    // const paddingTop = '60px';
    return (
      <>
        {this.loading() ? (
          <Loader active size={'massive'} />
        ) : (
          <Router>
            <>
              <ErrorBoundary>
                <Header
                  isAuthenticated={this.props.isAuthenticated}
                  isAdmin={this.props.isAdmin}
                  ribbonEnv={this.props.ribbonEnv}
                  isInProduction={this.props.isInProduction}
                  isSwaggerEnabled={this.props.isSwaggerEnabled}
                />
              </ErrorBoundary>
              <ErrorBoundary>
                <AppRoutes />
              </ErrorBoundary>
              {this.props.isAuthenticated && <Footer />}
            </>
          </Router>
        )}
      </>
    );
  }
}

const mapStateToProps = ({ authentication, applicationProfile }: IRootState) => ({
  isAuthenticated: authentication.isAuthenticated,
  isAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN]),
  ribbonEnv: applicationProfile.ribbonEnv,
  isInProduction: applicationProfile.inProduction,
  isSwaggerEnabled: applicationProfile.isSwaggerEnabled,
  isLoading: authentication.loading
});

const mapDispatchToProps = { getSession, getProfile };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
