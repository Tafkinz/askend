import { Alert } from '@mui/material';
import * as React from 'react';
import { ErrorInfo } from 'react';

interface IErrorBoundaryState {
  hasError: boolean;
}

interface IErrorBoundaryProps {
  children: React.JSX.Element | React.JSX.Element[] | string;
}

class ErrorBoundary extends React.Component<IErrorBoundaryProps, IErrorBoundaryState> {
  constructor(props: IErrorBoundaryProps) {
    super(props);
    this.state = {hasError: false};
  }

  componentDidCatch(error: Error, info: ErrorInfo) {
    console.error(
      error,
      info.componentStack,
      React.captureOwnerStack(),
    );
  }

  render() {
    if (this.state.hasError) {
      return <Alert severity="error">Unexpected error has occurred</Alert>;
    }

    return this.props.children;
  }
}

export { ErrorBoundary };
