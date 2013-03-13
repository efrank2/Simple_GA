public class Chromosome
{
	String plus=  "1010";
	String minus= "1011";
	String times= "1100";
	String div=   "1101";
	public String geneSequence;
	public Chromosome(String geneSequence)
	{
		this.geneSequence = geneSequence;
	}
//Performs operations from left to right without considering PEMDAS.
//Discounts impossible sequences and consecutive operators.
	public double geneDecoder()
	{
		int[] temp = new int[2];
		String tempOp = "";
		for(int i=0; i<geneSequence.length()/4; i++)
		{
			String tempS = geneSequence.substring(4*i,(4*i+4));
			if(!tempS.equals(plus) && !tempS.equals(minus) && !tempS.equals(times) &&!tempS.equals(div) && !tempS.equals("1110") && !tempS.equals("1111"))
			{
//Should've used an arraylist, but w.e.
				if(temp[0] == 0)
					temp[0]=Integer.parseInt(tempS, 2);
				else
					temp[1]=Integer.parseInt(tempS, 2);
			}
			else
				tempOp = tempS;
			if((temp[1] != 0) && (!tempOp.equals(null)))
			{
				if(tempOp.equals(plus))
					temp[0] = temp[0] + temp[1];
				else if(tempOp.equals(minus))
					temp[0] = temp[0] - temp[1];
				else if(tempOp.equals(times))
					temp[0] = temp[0] * temp[1];
				else if(tempOp.equals(div))
					temp[0] = temp[0] / temp[1];
			}
		}
		System.out.println(temp[0]);
		return temp[0];
	}
	public int length()
	{
		return geneSequence.length();
	}

}
	
