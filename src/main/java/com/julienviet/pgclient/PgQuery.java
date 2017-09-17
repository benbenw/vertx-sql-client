/*
 * Copyright (C) 2017 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.julienviet.pgclient;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.sql.ResultSet;

/**
 * The binding of a {@link PgPreparedStatement} and a set of parameters that can be executed once.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface PgQuery {

  /**
   * Set the query result fetch size.
   * <p/>
   * The default fetch size is {@code 0} which means that all rows will be fetched at once.
   * <p/>
   *
   * @param size how many items should be fetched at once
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  PgQuery fetch(int size);

  /**
   * @return true if the query is in progress and has more results to fetch
   */
  boolean inProgress();

  /**
   * @return true if the query execution is has been completed.
   */
  boolean completed();

  /**
   * Executes the query, the {@code handler} will be called when the result set is available or
   * if an error occurs.
   * <p/>
   * Setting a fetch size positive value {@code N } asks Postgres to send at most {@code N} rows.
   * When the query is executed the method {@link #inProgress()} ()} tells wether
   * there is remaining data to fetch or not.
   * <p/>
   * Additional unfetched data shall be fetch using extra calls {@code execute} until the result set is complete.
   * <p/>
   *
   * @param handler the handler to call back
   */
  void execute(Handler<AsyncResult<ResultSet>> handler);

  /**
   * When the query is managing a cursor, the corresponding resources is released. You don't need
   * to close the query when the query was executed fully.
   */
  void close();

  /**
   * Like {@link #close()} but with a completion handler to be notified of the result.
   */
  void close(Handler<AsyncResult<Void>> completionHandler);

}