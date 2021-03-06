// OO jDREW - An Object Oriented extension of the Java Deductive Reasoning Engine for the Web
// Copyright (C) 2005 Marcel Ball
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

package org.ruleml.oojdrew.Builtins;

import java.util.Vector;

import org.ruleml.oojdrew.util.DefiniteClause;
import org.ruleml.oojdrew.util.SymbolTable;
import org.ruleml.oojdrew.util.Term;
import org.ruleml.oojdrew.util.Types;

/**
 * Implements a Substring built in relation.
 *
 * Satisfied iff the first argument is equal to the substring of 
 * optional length the fourth argument starting at character offset
 * the third argument in the string the second argument.
 *
 * If the first argument is a variable then it will be bound to
 * the substring of optional length the fourth argument starting at
 * character offset the third argument in the string the second argument.
 * 
 * <p>Title: OO jDREW</p>
 *
 * <p>Description: Reasoning Engine for the Semantic Web - Supporting OO RuleML
 * 0.88</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Marcel A. Ball
 * @version 0.89
 */
public class SubstringBuiltin implements Builtin {
    private int symbol = SymbolTable.internSymbol("substring");

    public DefiniteClause buildResult(Term t) {
        if (t.getSymbol() != symbol) {
            return null;
        }

        if (t.subTerms.length == 4) {
            Term p2 = t.subTerms[2].deepCopy();
            Term p3 = t.subTerms[3].deepCopy();

            if (p2.getSymbol() < 0 || p3.getSymbol() < 0) {
                return null;
            }

            if (p2.getType() != Types.ISTRING || p3.getType() != Types.IINTEGER) {
                return null;
            }

            String p2s = p2.getSymbolString();
            String p3s = p3.getSymbolString();
            int p3i;
            try {
                p3i = Integer.parseInt(p3s);
            } catch (Exception e) {
                return null;
            }

            String results = p2s.substring(p3i);

            Term r1 = new Term(SymbolTable.internSymbol(results),
                               SymbolTable.INOROLE, Types.ISTRING);
            Term roid = new Term(SymbolTable.internSymbol("$jdrew-substring-"
                                                          + p2s + "-" + p3s),
                                 SymbolTable.IOID, Types.ITHING);
            Vector v = new Vector();
            v.add(roid);
            v.add(r1);
            v.add(p2);
            v.add(p3);

            Term atm = new Term(symbol, SymbolTable.INOROLE, Types.IOBJECT, v);
            atm.setAtom(true);
            Vector v2 = new Vector();
            v2.add(atm);
            return new DefiniteClause(v2, new Vector());
        } else if (t.subTerms.length == 5) {
            Term p2 = t.subTerms[2].deepCopy();
            Term p3 = t.subTerms[3].deepCopy();
            Term p4 = t.subTerms[4].deepCopy();
            if (p2.getSymbol() < 0 || p3.getSymbol() < 0 || p4.getSymbol() < 0) {
                return null;
            }

            if (p2.getType() != Types.ISTRING || p3.getType() != Types.IINTEGER ||
                p4.getType() != Types.IINTEGER) {
                return null;
            }

            String p2s = p2.getSymbolString();
            String p3s = p3.getSymbolString();
            String p4s = p4.getSymbolString();

            int p3i;
            int p4i;
            try {
                p3i = Integer.parseInt(p3s);
                p4i = Integer.parseInt(p4s);
            } catch (Exception e) {
                return null;
            }

            String results = p2s.substring(p3i, p4i);

            Term r1 = new Term(SymbolTable.internSymbol(results),
                               SymbolTable.INOROLE, Types.ISTRING);
            Term roid = new Term(SymbolTable.internSymbol("$jdrew-substring-" + p2s + "-" + p3s + "-" + p4s),
                                 SymbolTable.IOID, Types.ITHING);
            Vector v = new Vector();
            v.add(roid);
            v.add(r1);
            v.add(p2);
            v.add(p3);
            v.add(p4);

            Term atm = new Term(symbol, SymbolTable.INOROLE, Types.IOBJECT, v);
            atm.setAtom(true);
            Vector v2 = new Vector();
            v2.add(atm);
            return new DefiniteClause(v2, new Vector());
        } else {
            return null;
        }
    }

    public int getSymbol() {
        return symbol;
    }


}
