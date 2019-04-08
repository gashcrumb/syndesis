import { UnrecoverableError } from '@syndesis/ui';
import * as React from 'react';
import { Translation } from 'react-i18next';

export interface IWithErrorBoundaryState {
  error?: Error;
  errorInfo?: React.ErrorInfo;
}

export class WithErrorBoundary extends React.Component<
  any,
  IWithErrorBoundaryState
> {
  public state: IWithErrorBoundaryState = {};

  public componentDidCatch(error: Error, errorInfo: React.ErrorInfo): void {
    this.setState({
      error,
      errorInfo,
    });
  }

  public componentWillReceiveProps(
    nextProps: Readonly<any>,
    nextContext: any
  ): void {
    this.setState({
      error: undefined,
      errorInfo: undefined,
    });
  }

  public render() {
    return this.state.error && this.state.errorInfo ? (
      <Translation ns={['shared']}>
        {t => (
          <UnrecoverableError
            i18nTitle={t('error.title')}
            i18nInfo={t('error.info')}
            i18nHelp={t('error.help')}
            i18nRefreshLabel={t('error.refreshButton')}
            i18nShowErrorInfoLabel={t('error.showErrorInfoButton')}
            error={this.state.error}
            errorInfo={this.state.errorInfo}
          />
        )}
      </Translation>
    ) : (
      this.props.children
    );
  }
}
