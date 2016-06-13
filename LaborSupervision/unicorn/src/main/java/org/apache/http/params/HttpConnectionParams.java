/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.params;

import org.apache.http.util.Args;

/**
 * <<<<<<< HEAD
 * Utility class for accessing connection parameters in {@link HttpParams}.
 * =======
 * Utility class for accessing connection parameters in {@link org.apache.http.params.HttpParams}.
 * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
 *
 * @since 4.0
 * @deprecated (4.3) use configuration classes provided 'org.apache.http.config'
 * and 'org.apache.http.client.config'
 */
@Deprecated
public final class HttpConnectionParams implements CoreConnectionPNames
{

    private HttpConnectionParams()
    {
        super();
    }

    /**
     * <<<<<<< HEAD
     * Obtains value of the {@link CoreConnectionPNames#SO_TIMEOUT} parameter.
     * =======
     * Obtains value of the {@link org.apache.http.params.CoreConnectionPNames#SO_TIMEOUT} parameter.
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * If not set, defaults to <code>0</code>.
     *
     * @param params
     *         HTTP parameters.
     *
     * @return SO_TIMEOUT.
     */
    public static int getSoTimeout(final HttpParams params)
    {
        Args.notNull(params, "HTTP parameters");
        return params.getIntParameter(CoreConnectionPNames.SO_TIMEOUT, 0);
    }

    /**
     * <<<<<<< HEAD
     * Sets value of the {@link CoreConnectionPNames#SO_TIMEOUT} parameter.
     * =======
     * Sets value of the {@link org.apache.http.params.CoreConnectionPNames#SO_TIMEOUT} parameter.
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     *
     * @param params
     *         HTTP parameters.
     * @param timeout
     *         SO_TIMEOUT.
     */
    public static void setSoTimeout(final HttpParams params, final int timeout)
    {
        Args.notNull(params, "HTTP parameters");
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);

    }

    /**
     * <<<<<<< HEAD
     * Obtains value of the {@link CoreConnectionPNames#SO_REUSEADDR} parameter.
     * =======
     * Obtains value of the {@link org.apache.http.params.CoreConnectionPNames#SO_REUSEADDR} parameter.
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * If not set, defaults to <code>false</code>.
     *
     * @param params
     *         HTTP parameters.
     *
     * @return SO_REUSEADDR.
     *
     * @since 4.1
     */
    public static boolean getSoReuseaddr(final HttpParams params)
    {
        Args.notNull(params, "HTTP parameters");
        return params.getBooleanParameter(CoreConnectionPNames.SO_REUSEADDR, false);
    }

    /**
     * <<<<<<< HEAD
     * Sets value of the {@link CoreConnectionPNames#SO_REUSEADDR} parameter.
     * =======
     * Sets value of the {@link org.apache.http.params.CoreConnectionPNames#SO_REUSEADDR} parameter.
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     *
     * @param params
     *         HTTP parameters.
     * @param reuseaddr
     *         SO_REUSEADDR.
     *
     * @since 4.1
     */
    public static void setSoReuseaddr(final HttpParams params, final boolean reuseaddr)
    {
        Args.notNull(params, "HTTP parameters");
        params.setBooleanParameter(CoreConnectionPNames.SO_REUSEADDR, reuseaddr);
    }

    /**
     * <<<<<<< HEAD
     * Obtains value of the {@link CoreConnectionPNames#TCP_NODELAY} parameter.
     * =======
     * Obtains value of the {@link org.apache.http.params.CoreConnectionPNames#TCP_NODELAY} parameter.
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * If not set, defaults to <code>true</code>.
     *
     * @param params
     *         HTTP parameters.
     *
     * @return Nagle's algorithm flag
     */
    public static boolean getTcpNoDelay(final HttpParams params)
    {
        Args.notNull(params, "HTTP parameters");
        return params.getBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
    }

    /**
     * <<<<<<< HEAD
     * Sets value of the {@link CoreConnectionPNames#TCP_NODELAY} parameter.
     * =======
     * Sets value of the {@link org.apache.http.params.CoreConnectionPNames#TCP_NODELAY} parameter.
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     *
     * @param params
     *         HTTP parameters.
     * @param value
     *         Nagle's algorithm flag
     */
    public static void setTcpNoDelay(final HttpParams params, final boolean value)
    {
        Args.notNull(params, "HTTP parameters");
        params.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, value);
    }

    /**
     * <<<<<<< HEAD
     * Obtains value of the {@link CoreConnectionPNames#SOCKET_BUFFER_SIZE}
     * =======
     * Obtains value of the {@link org.apache.http.params.CoreConnectionPNames#SOCKET_BUFFER_SIZE}
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * parameter. If not set, defaults to <code>-1</code>.
     *
     * @param params
     *         HTTP parameters.
     *
     * @return socket buffer size
     */
    public static int getSocketBufferSize(final HttpParams params)
    {
        Args.notNull(params, "HTTP parameters");
        return params.getIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, -1);
    }

    /**
     * <<<<<<< HEAD
     * Sets value of the {@link CoreConnectionPNames#SOCKET_BUFFER_SIZE}
     * =======
     * Sets value of the {@link org.apache.http.params.CoreConnectionPNames#SOCKET_BUFFER_SIZE}
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * parameter.
     *
     * @param params
     *         HTTP parameters.
     * @param size
     *         socket buffer size
     */
    public static void setSocketBufferSize(final HttpParams params, final int size)
    {
        Args.notNull(params, "HTTP parameters");
        params.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, size);
    }

    /**
     * <<<<<<< HEAD
     * Obtains value of the {@link CoreConnectionPNames#SO_LINGER} parameter.
     * =======
     * Obtains value of the {@link org.apache.http.params.CoreConnectionPNames#SO_LINGER} parameter.
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * If not set, defaults to <code>-1</code>.
     *
     * @param params
     *         HTTP parameters.
     *
     * @return SO_LINGER.
     */
    public static int getLinger(final HttpParams params)
    {
        Args.notNull(params, "HTTP parameters");
        return params.getIntParameter(CoreConnectionPNames.SO_LINGER, -1);
    }

    /**
     * <<<<<<< HEAD
     * Sets value of the {@link CoreConnectionPNames#SO_LINGER} parameter.
     * =======
     * Sets value of the {@link org.apache.http.params.CoreConnectionPNames#SO_LINGER} parameter.
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     *
     * @param params
     *         HTTP parameters.
     * @param value
     *         SO_LINGER.
     */
    public static void setLinger(final HttpParams params, final int value)
    {
        Args.notNull(params, "HTTP parameters");
        params.setIntParameter(CoreConnectionPNames.SO_LINGER, value);
    }

    /**
     * <<<<<<< HEAD
     * Obtains value of the {@link CoreConnectionPNames#CONNECTION_TIMEOUT}
     * =======
     * Obtains value of the {@link org.apache.http.params.CoreConnectionPNames#CONNECTION_TIMEOUT}
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * parameter. If not set, defaults to <code>0</code>.
     *
     * @param params
     *         HTTP parameters.
     *
     * @return connect timeout.
     */
    public static int getConnectionTimeout(final HttpParams params)
    {
        Args.notNull(params, "HTTP parameters");
        return params.getIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 0);
    }

    /**
     * <<<<<<< HEAD
     * Sets value of the {@link CoreConnectionPNames#CONNECTION_TIMEOUT}
     * =======
     * Sets value of the {@link org.apache.http.params.CoreConnectionPNames#CONNECTION_TIMEOUT}
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * parameter.
     *
     * @param params
     *         HTTP parameters.
     * @param timeout
     *         connect timeout.
     */
    public static void setConnectionTimeout(final HttpParams params, final int timeout)
    {
        Args.notNull(params, "HTTP parameters");
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
    }

    /**
     * <<<<<<< HEAD
     * Obtains value of the {@link CoreConnectionPNames#STALE_CONNECTION_CHECK}
     * =======
     * Obtains value of the {@link org.apache.http.params.CoreConnectionPNames#STALE_CONNECTION_CHECK}
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * parameter. If not set, defaults to <code>true</code>.
     *
     * @param params
     *         HTTP parameters.
     *
     * @return stale connection check flag.
     */
    public static boolean isStaleCheckingEnabled(final HttpParams params)
    {
        Args.notNull(params, "HTTP parameters");
        return params.getBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
    }

    /**
     * <<<<<<< HEAD
     * Sets value of the {@link CoreConnectionPNames#STALE_CONNECTION_CHECK}
     * =======
     * Sets value of the {@link org.apache.http.params.CoreConnectionPNames#STALE_CONNECTION_CHECK}
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * parameter.
     *
     * @param params
     *         HTTP parameters.
     * @param value
     *         stale connection check flag.
     */
    public static void setStaleCheckingEnabled(final HttpParams params, final boolean value)
    {
        Args.notNull(params, "HTTP parameters");
        params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, value);
    }

    /**
     * <<<<<<< HEAD
     * Obtains value of the {@link CoreConnectionPNames#SO_KEEPALIVE} parameter.
     * =======
     * Obtains value of the {@link org.apache.http.params.CoreConnectionPNames#SO_KEEPALIVE} parameter.
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     * If not set, defaults to <code>false</code>.
     *
     * @param params
     *         HTTP parameters.
     *
     * @return SO_KEEPALIVE.
     *
     * @since 4.2
     */
    public static boolean getSoKeepalive(final HttpParams params)
    {
        Args.notNull(params, "HTTP parameters");
        return params.getBooleanParameter(CoreConnectionPNames.SO_KEEPALIVE, false);
    }

    /**
     * <<<<<<< HEAD
     * Sets value of the {@link CoreConnectionPNames#SO_KEEPALIVE} parameter.
     * =======
     * Sets value of the {@link org.apache.http.params.CoreConnectionPNames#SO_KEEPALIVE} parameter.
     * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
     *
     * @param params
     *         HTTP parameters.
     * @param enableKeepalive
     *         SO_KEEPALIVE.
     *
     * @since 4.2
     */
    public static void setSoKeepalive(final HttpParams params, final boolean enableKeepalive)
    {
        Args.notNull(params, "HTTP parameters");
        params.setBooleanParameter(CoreConnectionPNames.SO_KEEPALIVE, enableKeepalive);
    }

}
