import { WebPlugin } from '@capacitor/core';

import type {
  BeginTransactionOptions,
  BeginTransactionResult,
  CommitTransactionOptions,
  ConnectOptions,
  ConnectResult,
  ExecuteBatchOptions,
  ExecuteOptions,
  LibsqlPlugin,
  QueryOptions,
  QueryResult,
  RollbackTransactionOptions,
  SyncOptions,
} from './definitions';

export class LibsqlWeb extends WebPlugin implements LibsqlPlugin {
  async beginTransaction(
    options: BeginTransactionOptions,
  ): Promise<BeginTransactionResult> {
    console.log('beginTransaction', options);
    throw this.unimplemented('Not implemented on web.');
  }

  async commitTransaction(options: CommitTransactionOptions): Promise<void> {
    console.log('commitTransaction', options);
    throw this.unimplemented('Not implemented on web.');
  }

  async connect(options: ConnectOptions): Promise<ConnectResult> {
    console.log('connect', options);
    throw this.unimplemented('Not implemented on web.');
  }

  async execute(options: ExecuteOptions): Promise<void> {
    console.log('execute', options);
    throw this.unimplemented('Not implemented on web.');
  }

  async executeBatch(options: ExecuteBatchOptions): Promise<void> {
    console.log('executeBatch', options);
    throw this.unimplemented('Not implemented on web.');
  }

  async query(options: QueryOptions): Promise<QueryResult> {
    console.log('query', options);
    throw this.unimplemented('Not implemented on web.');
  }

  async rollbackTransaction(
    options: RollbackTransactionOptions,
  ): Promise<void> {
    console.log('rollbackTransaction', options);
    throw this.unimplemented('Not implemented on web.');
  }

  async sync(options: SyncOptions): Promise<void> {
    console.log('sync', options);
    throw this.unimplemented('Not implemented on web.');
  }
}
