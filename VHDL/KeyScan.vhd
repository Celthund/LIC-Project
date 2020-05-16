----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:51:34 05/13/2020 
-- Design Name: 
-- Module Name:    KeyScan - Structural 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity KeyScan is
    Port ( 
			RST: in STD_LOGIC;
			CLK : in  STD_LOGIC;
			KScan : in  STD_LOGIC_VECTOR (1 downto 0);
			K : out  STD_LOGIC_VECTOR (3 downto 0);
			PENC_IN: in  STD_LOGIC_VECTOR (3 downto 0);
			KPress : out  STD_LOGIC;
			DEC_OUT : buffer STD_LOGIC_VECTOR (2 downto 0)
	 );
end KeyScan;

architecture Structural of KeyScan is
	COMPONENT Register_D
		GENERIC (
			WIDTH : POSITIVE 
		);
		PORT(
			CLK : IN std_logic;
			RST : IN std_logic;
			D : IN std_logic_vector(WIDTH-1 downto 0);          
			Q : OUT std_logic_vector(WIDTH-1 downto 0)
		);
	END COMPONENT;
	COMPONENT PriorityEnconder
		Port (
			  I0 : in  STD_LOGIC;
           I1 : in  STD_LOGIC;
           I2 : in  STD_LOGIC;
           I3 : in  STD_LOGIC;
           Y : out  STD_LOGIC_VECTOR (1 downto 0);
           GS : out  STD_LOGIC
	  );
	END COMPONENT;
	COMPONENT Decoder
		Port (
			 S0 : in  STD_LOGIC;
			 S1 : in  STD_LOGIC;
		    O : out STD_LOGIC_VECTOR (2 downto 0)
		);
	END COMPONENT;
	COMPONENT Counter2bit
		Port ( 
			  CE : in  STD_LOGIC;
           CLK : in  STD_LOGIC;
           RST : in  STD_LOGIC;
           Q : buffer  STD_LOGIC_VECTOR (1 downto 0)
	 );
	END COMPONENT;
	SIGNAL QS, YReg : STD_LOGIC_VECTOR (1 downto 0);
	SIGNAL NOT_DECOUT : STD_LOGIC_VECTOR (2 downto 0);
begin
	U0 : Counter2bit 
	PORT MAP(
			CE => KScan(1),
			RST => RST,
			CLK =>  CLK,
			Q => QS
	);
	U1 : Register_D 
	GENERIC MAP(
		WIDTH => 2
	)
	PORT MAP(
			CLK => KScan(0),
			RST => RST,
			D => YReg,
			Q(0) => K(0),
			Q(1) => K(1)
	);
	U2 : Decoder 
	PORT MAP(
			S0 => QS(0),
			S1 => QS(1),
		   O => NOT_DECOUT
	);
	U3 : PriorityEnconder 
	PORT MAP(
			  I0 => not PENC_IN(0),
           I1 => not PENC_IN(1),
           I2 => not PENC_IN(2),
           I3 => not PENC_IN(3),
           Y => YReg,
           GS => KPress
	);
	K(2) <= QS(0);
	K(3) <= QS(1);
	DEC_OUT <= not NOT_DECOUT;
end Structural;

