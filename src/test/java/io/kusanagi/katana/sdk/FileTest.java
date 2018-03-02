/*
 * Java 8 SDK for the KATANA(tm) Platform (http://katana.kusanagi.io)
 * Copyright (c) 2016-2017 KUSANAGI S.L. All rights reserved.
 *
 * Distributed under the MIT license
 *
 * For the full copyright and license information, please view the LICENSE
 *  file that was distributed with this source code
 *
 * @link      https://github.com/kusanagi/katana-sdk-java8
 * @license   http://www.opensource.org/licenses/mit-license.php MIT License
 * @copyright Copyright (c) 2016-2017 KUSANAGI S.L. (http://kusanagi.io)
 *
 */

package io.kusanagi.katana.sdk;

import io.kusanagi.katana.utils.MockFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by juane on 2/11/17.
 */
public class FileTest {

    @Test
    public void equalsAndHashcode() {
        MockFactory mockFactory = new MockFactory();
        File object = mockFactory.getFile();
        Assert.assertEquals(object, new File(object));
        Assert.assertEquals(-1592085337, object.hashCode());
        Assert.assertEquals(
                "File{name='avatar', path='http://12.34.56.78:1234/files/ac3bd4b8-7da3-4c40-8661-746adfa55e0d', mime='image/jpeg', filename='smiley.jpg', size='1234567890', token='fb9an6c46be74s425010896fcbd99e2a', exists=false}",
                object.toString());
    }

}