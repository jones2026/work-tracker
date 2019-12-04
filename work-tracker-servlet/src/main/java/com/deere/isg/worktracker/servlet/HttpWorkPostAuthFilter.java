/**
 * Copyright 2019 Deere & Company
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.deere.isg.worktracker.servlet;

import com.deere.isg.worktracker.OutstandingWork;
import com.deere.isg.worktracker.OutstandingWorkTracker;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpWorkPostAuthFilter extends BaseTypeFilter {
    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (getOutstanding() != null) {
            ((OutstandingWorkTracker<? extends HttpWork>) getOutstanding()).current()
                    .ifPresent(work -> addUserInformation((HttpServletRequest) request, work));
        }
        chain.doFilter(request, response);
    }

    private void addUserInformation(HttpServletRequest request, HttpWork work) {
        work.updateUserInformation(request);
    }

    protected void setFilterOutstanding(OutstandingWork outstanding) {
        setOutstanding(outstanding);
    }
}