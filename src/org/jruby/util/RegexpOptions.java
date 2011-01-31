/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jruby.util;

import org.jcodings.Encoding;
import org.jruby.RubyRegexp;

public class RegexpOptions implements Cloneable {
    public static final RegexpOptions NULL_OPTIONS = new RegexpOptions(null, KCode.NONE.getEncoding());
    
    public RegexpOptions() {
        this(null, KCode.NONE.getEncoding());
    }
    
    public RegexpOptions(KCode kcode, Encoding encoding) {
        this.kcode = kcode;
        this.encoding = encoding;
    }
    
    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public boolean isIgnorecase() {
        return ignorecase;
    }

    public void setIgnorecase(boolean ignorecase) {
        this.ignorecase = ignorecase;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public KCode getKCode() {
        return kcode;
    }

    public void setKCode(KCode kcode) {
        this.kcode = kcode;
        this.encoding = kcode.getEncoding();
    }

    public boolean isMultiline() {
        return multiline;
    }

    public void setMultiline(boolean multiline) {
        this.multiline = multiline;
    }

    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public boolean isJava() {
        return java;
    }

    public void setJava(boolean java) {
        this.java = java;
    }

    public boolean isEncodingNone() {
        return encodingNone;
    }

    public void setEncodingNone(boolean encodingNone) {
        this.encodingNone = encodingNone;
    }

    public boolean isKcodeDefault() {
        return kcodeDefault;
    }

    public void setKcodeDefault(boolean kcodeDefault) {
        this.kcodeDefault = kcodeDefault;
    }

    public boolean isLiteral() {
        return literal;
    }

    public void setLiteral(boolean literal) {
        this.literal = literal;
    }

    public boolean isEmbeddable() {
        return multiline && ignorecase && extended;
    }

    public int toJoniOptions() {
        int options = 0;
        if (multiline) options |= RubyRegexp.RE_OPTION_MULTILINE;
        if (ignorecase) options |= RubyRegexp.RE_OPTION_IGNORECASE;
        if (extended) options |= RubyRegexp.RE_OPTION_EXTENDED;
        if (once) options |= RubyRegexp.RE_OPTION_ONCE;
        if (kcode != null) options |= kcode.bits();
        return options;
    }

    public static RegexpOptions fromJoniOptions(int joniOptions) {
        RegexpOptions options = new RegexpOptions();
        options.setMultiline((joniOptions & RubyRegexp.RE_OPTION_MULTILINE) != 0);
        options.setIgnorecase((joniOptions & RubyRegexp.RE_OPTION_IGNORECASE) != 0);
        options.setExtended((joniOptions & RubyRegexp.RE_OPTION_EXTENDED) != 0);
        options.setOnce((joniOptions & RubyRegexp.RE_OPTION_ONCE) != 0);
        KCode kcode = KCode.fromBits(joniOptions);
        if (kcode != KCode.NONE) {
            // ENEBO: This is not so clear...if we use fromJoniOptions for 
            // replication from another regexp we lose info like kcodefault
            options.setKCode(kcode);
        }
        return options;
    }

    public RegexpOptions withoutOnce() {
        RegexpOptions options = (RegexpOptions)clone();
        options.setOnce(false);
        return options;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.encoding != null ? this.encoding.hashCode() : 0);
        hash = 11 * hash + (this.kcode != null ? this.kcode.hashCode() : 0);
        hash = 11 * hash + (this.fixed ? 1 : 0);
        hash = 11 * hash + (this.once ? 1 : 0);
        hash = 11 * hash + (this.extended ? 1 : 0);
        hash = 11 * hash + (this.multiline ? 1 : 0);
        hash = 11 * hash + (this.ignorecase ? 1 : 0);
        hash = 11 * hash + (this.java ? 1 : 0);
        hash = 11 * hash + (this.encodingNone ? 1 : 0);
        hash = 11 * hash + (this.kcodeDefault ? 1 : 0);
        hash = 11 * hash + (this.literal ? 1 : 0);
        return hash;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cnse) {throw new RuntimeException(cnse);}
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RegexpOptions)) return false;

        RegexpOptions o = (RegexpOptions)other;
//        System.out.println("THIS: " + this);
//        System.out.println("OTHR: " + other);
        return
                o.encoding == encoding &&
                o.encodingNone == encodingNone &&
                o.extended == extended &&
                o.fixed == fixed &&
                o.ignorecase == ignorecase &&
                o.java == java &&
                o.kcode == kcode &&
                o.kcodeDefault == kcodeDefault &&
                o.literal == literal &&
                o.multiline == multiline &&
                o.once == once;
    }
    
    @Override
    public String toString() {
        return "RegexpOptions(encoding: " + encoding + ", kcode: " + kcode + 
                (encodingNone == true ? ", encodingNone" : "") +
                (extended == true ? ", extended" : "") +
                (fixed == true ? ", fixed" : "") +
                (ignorecase == true ? ", ignorecase" : "") +
                (java == true ? ", java" : "") +
                (kcodeDefault == true ? ", kcodeDefault" : "") +
                (literal == true ? ", literal" : "") +
                (multiline == true ? ", multiline" : "") +
                (once == true ? ", once" : "") +                
                ")";
    }
    
    private Encoding encoding;
    private KCode kcode;
    private boolean fixed;
    private boolean once;
    private boolean extended;
    private boolean multiline;
    private boolean ignorecase;
    private boolean java;
    private boolean encodingNone;
    private boolean kcodeDefault;
    private boolean literal;
    private boolean embedded;
}
